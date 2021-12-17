#!/usr/bin/env scopes

let rl = (import .raylib)
using rl.macros

let
    screen_width = 800
    screen_height = 450

let ball_position =
    rl.Vector2
        screen_width / 2
        screen_height / 2

do-window:
    800
    450
    "raylib [core] Scopes example - core basic window"
    60

    if (rl.IsKeyDown rl.KEY_RIGHT)
        ball_position.x +


    do-draw:
        
