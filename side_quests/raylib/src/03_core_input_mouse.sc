#!/usr/bin/env scopes

let rl = (import .raylib)
using rl.macros

let
    screen_width = 800
    screen_height = 450

local ball_position =
    rl.Vector2
        -100:f32
        -100:f32

let default_color = rl.Colors.DARKBLUE

local ball_color = default_color


do-window:
    screen_width
    screen_height
    "raylib [core] Scopes example - mouse input"
    60

    ball_position = (rl.GetMousePosition)

    if (rl.IsMouseButtonPressed rl.MOUSE_BUTTON_LEFT)
        ball_color = rl.Colors.MAROON

    elseif (rl.IsMouseButtonPressed rl.MOUSE_BUTTON_MIDDLE)
        ball_color = rl.Colors.LIME

    elseif (rl.IsMouseButtonPressed rl.MOUSE_BUTTON_RIGHT)
        ball_color = rl.Colors.DARKBLUE

    elseif (rl.IsMouseButtonPressed rl.MOUSE_BUTTON_SIDE)
        ball_color = rl.Colors.PURPLE

    elseif (rl.IsMouseButtonPressed rl.MOUSE_BUTTON_EXTRA)
        ball_color = rl.Colors.YELLOW

    elseif (rl.IsMouseButtonPressed rl.MOUSE_BUTTON_FORWARD)
        ball_color = rl.Colors.ORANGE

    elseif (rl.IsMouseButtonPressed rl.MOUSE_BUTTON_BACK)
        ball_color = rl.Colors.BEIGE


    do-draw:

        (rl.ClearBackground rl.Colors.RAYWHITE)

        rl.DrawText
            "Move ball with mouse and click mouse buttons to change color"
            10
            10
            20
            rl.Colors.DARKGRAY

        rl.DrawCircleV
            ball_position
            40
            ball_color
