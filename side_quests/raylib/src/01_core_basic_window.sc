#!/usr/bin/env scopes

let rl = (import .raylib)
using rl.macros

do-window:
    800
    450
    "raylib [core] Scopes example - core basic window"
    60

    do-draw:

        rl.ClearBackground rl.Colors.RAYWHITE

        rl.DrawText
            "Congrats! You created your first window!"
            190
            200
            20
            rl.Colors.LIGHTGRAY
