let rl = (import .raylib)
using rl.macros

let MAX_BUILDINGS = 100

let
    screen_width = 800
    screen_height = 450

## The player sprite
local player =
    rl.Rectangle
        400
        280
        40
        40

## Generate the Buildings in the scene
local buildings = ((array rl.Rectangle MAX_BUILDINGS))

local building_colors = ((array rl.Color MAX_BUILDINGS))

local spacing = 0:f32

for i in (range MAX_BUILDINGS)

    ((buildings @ i) . width) = ((rl.GetRandomValue 50 200) as f32)
    ((buildings @ i) . height) = ((rl.GetRandomValue 100 800) as f32)
    ((buildings @ i). y) = screen_height - 130.0:f32 - ((buildings @ i) . height)
    ((buildings @ i) . x) = -6000:f32 + spacing

    # TODO turn this into a foldby loop
    spacing += ((buildings @ i) . width)

    (building_colors @ i) =
        rl.Color
            ((rl.GetRandomValue 200 240) as u8)
            ((rl.GetRandomValue 200 240) as u8)
            ((rl.GetRandomValue 200 250) as u8)
            255

## Border
let border_width = 5
let sky_gradient = (rl.Fade rl.Colors.SKYBLUE 0.5:f32)


## Camera
local cam_target = (rl.Vector2 (player.x + 20.0) (player.y + 20.0))
local cam_offset = (rl.Vector2 (screen_width / 2.0) (screen_height / 2.0))

local camera =
    rl.Camera2D
        cam_target
        cam_offset
        0.0
        1.0

let cam_rotation_limit = 40

do-window:
    screen_width
    screen_height
    "raylib [core] Scopes example - 2d camera"
    60

    # move the player around
    if (rl.IsKeyDown rl.KEY_RIGHT)
        player.x += 2
    elseif (rl.IsKeyDown rl.KEY_LEFT)
        player.x -= 2

    ## Camera control

    # make the camera follow the player
    (camera . target) = (rl.Vector2 (player.x + 20.0) (player.y + 20.0))

    # control rotation
    if (rl.IsKeyDown rl.KEY_A)
        camera.rotation -= 1
    elseif(rl.IsKeyDown rl.KEY_S)
        camera.rotation += 1

    # limit rotation
    if (camera.rotation > cam_rotation_limit)
        camera.rotation = cam_rotation_limit
    if (camera.rotation < -cam_rotation_limit)
        camera.rotation = -cam_rotation_limit

    # camera zoom
    camera.zoom += (rl.GetMouseWheelMove; * 0.1)

    if (camera.zoom > 3.0)
        camera.zoom = 3.0
    elseif (camera.zoom < 0.1)
        camera.zoom = 0.1

    if (rl.IsKeyPressed rl.KEY_R)
        camera.zoom = 1.0
        camera.rotation = 0.0

    do-draw:

        (rl.ClearBackground rl.Colors.RAYWHITE)

        ## The camera stuff
        (rl.BeginMode2D camera)

        ## Draw the scene

        # the ground
        rl.DrawRectangle
            -6000
            320
            13000
            8000
            rl.Colors.DARKGRAY

        # draw the buildings
        for i in (range MAX_BUILDINGS)

            rl.DrawRectangleRec
                (buildings @ i)
                (building_colors @ i)

        # draw the player
        rl.DrawRectangleRec
            player
            rl.Colors.RED

        # draw a reticule for the camera
        rl.DrawLine
            ((camera . target) . x) as i32
            -screen_height * 10
            ((camera . target) . x) as i32
            screen_height * 10
            rl.Colors.GREEN

        rl.DrawLine
            -screen_width * 10
            ((camera . target) . y) as i32
            screen_width * 10
            ((camera . target) . y) as i32
            rl.Colors.GREEN

        ## Draw the HUD stuff

        rl.DrawText
            "SCREEN AREA"
            640
            10
            20
            rl.Colors.RED

        # draw a border around the window
        rl.DrawRectangle
            0
            0
            screen_width
            border_width
            rl.Colors.RED

        rl.DrawRectangle
            0
            border_width
            border_width
            screen_height - (2 * border_width)
            rl.Colors.RED

        rl.DrawRectangle
            screen_width - border_width
            border_width
            border_width
            screen_height - (2 * border_width)
            rl.Colors.RED

        rl.DrawRectangle
            0
            screen_height - border_width
            screen_width
            border_width
            rl.Colors.RED

        # draw a dialog box

        rl.DrawRectangle
            10
            10
            250
            113
            sky_gradient

        rl.DrawRectangleLines
            10
            10
            250
            113
            rl.Colors.BLUE

        rl.DrawText
            "Free 2d camera controls:"
            20
            20
            10
            rl.Colors.BLACK

        rl.DrawText
            "- Right/Left to move Offset"
            40
            40
            10
            rl.Colors.DARKGRAY

        rl.DrawText
            "- Mouse Wheel to Zoom in-out"
            40
            60
            10
            rl.Colors.DARKGRAY

        rl.DrawText
            "- A / S to Rotate"
            40
            80
            10
            rl.Colors.DARKGRAY

        rl.DrawText
            "- R to reset Zoom and Rotation"
            40
            100
            10
            rl.Colors.DARKGRAY

