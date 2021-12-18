#!/usr/bin/env scopes

let rl = (import .raylib)
using rl.macros

let
    screen_width = 800
    screen_height = 450

local ball_position =
    rl.Vector2
        screen_width / 2
        screen_height / 2



do-window:
    screen_width
    screen_height
    "raylib [core] Scopes example - core basic window"
    60

    if (rl.IsKeyDown rl.KEY_RIGHT)
        ball_position.x = ball_position.x + 2.0:f32

    if (rl.IsKeyDown rl.KEY_LEFT)
        ball_position.x = ball_position.x - 2.0:f32

    if (rl.IsKeyDown rl.KEY_UP)
        ball_position.y = ball_position.y - 2.0:f32

    if (rl.IsKeyDown rl.KEY_DOWN)
        ball_position.y = ball_position.y + 2.0:f32


    do-draw:

        (rl.ClearBackground rl.Colors.RAYWHITE)

        rl.DrawText
            "Move the ball with the arrow keys"
            10
            10
            20
            rl.Colors.DARKGRAY

        rl.DrawCircleV
            ball_position
            50
            rl.Colors.MAROON
