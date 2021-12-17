let raylib-repo-path = "third_party/raylib-4.0.0"
let raylib-lib-path = (.. module-dir "/../" raylib-repo-path "/libraylib.so")
let raylib-include-path = (.. module-dir "/../" raylib-repo-path "/src/")

let header =
    include
        "raylib.h"
        options
            # "-v"
            .. "-I" raylib-include-path

load-library raylib-lib-path

# add the colors since they don't get parse properly from the header
let Color = header.struct.Color
vvv bind Colors
do
    let
        LIGHTGRAY =  (Color 200 200 200 255)
        GRAY = (Color 130 130 130 255)
        DARKGRAY = (Color 80 80 80 255)
        YELLOW = (Color 253 249 0 255)
        GOLD = (Color 255 203 0 255)
        ORANGE = (Color 255 161 0 255)
        PINK = (Color 255 109 194 255)
        RED = (Color 230 41 55 255)
        MAROON = (Color 190 33 55 255)
        GREEN = (Color 0 228 48 255)
        LIME = (Color 0 158 47 255)
        DARKGREEN = (Color 0 117 44 255)
        SKYBLUE = (Color 102 191 255 255)
        BLUE = (Color 0 121 241 255)
        DARKBLUE = (Color 0 82 172 255)
        PURPLE = (Color 200 122 255 255)
        VIOLET = (Color 135 60 190 255)
        DARKPURPLE = (Color 112 31 126 255)
        BEIGE  = (Color 211 176 131 255)
        BROWN = (Color 127 106 79 255)
        DARKBROWN = (Color 76 63 47 255)
        WHITE = (Color 255 255 255 255)
        BLACK = (Color 0 0 0 255)
        BLANK = (Color 0 0 0 0)
        MAGENTA = (Color 255 0 255 255)
        RAYWHITE = (Color 245 245 245 255)

    locals;


## dump the namespaces for the differnt header things
let raylib =
    ..
        header.extern
        header.typedef
        header.define
        header.const
        header.struct

# alias for local stuff
let rl = raylib

## add in some nice macros for easier setup etc.
sugar do-draw: (body...)
    qq
        do
            rl.BeginDrawing;
            unquote-splice body...
            rl.EndDrawing;

sugar do-window: (width height title fps body...)
    let width height title
    qq
        do
            rl.InitWindow
                [width]
                [height]
                [title]
            (rl.SetTargetFPS [fps])
            while (not (rl.WindowShouldClose))
                unquote-splice body...

            rl.CloseWindow;

let macros =
    'bind-symbols (Scope)
        do-draw: = do-draw:
        do-window: = do-window:


do
    let
        header
        raylib-repo-path
        raylib-lib-path
        raylib-include-path
        Colors
        do-draw:
        do-window:
        macros

    .. raylib (locals)
