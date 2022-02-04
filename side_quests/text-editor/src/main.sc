using import struct
using import String
using import Option
using import enum
(from (import UTF-8) let char32)


import C.string
import C.stdlib
import C.stdio

let C:unistd = (include "unistd.h")

# for ctype there are a bunch of macros so we need to make some
# wrappers around them so the compiler can read them properly
vvv bind C:ctype
include
    """"#include <ctype.h>
        typeof(iscntrl('a')) patched_iscntrl(char c) {
            return iscntrl(c);
        }

# locals
let termios = (import .termios)
using termios.macros

import .util

## Constants

# in tenths of a second how long read times out on
let TERMIOS_VTIME = 1:u8

let CTRL_KEY_BITMASK = 0x1f

## Structs
enum EditorActions
    Nothing
    Terminate

# enum Keys plain
#     C-q = 

## Functions
fn read_char (input)

    C:unistd.extern.read
        C:unistd.define.STDIN_FILENO
        (& input)
        1

    input

# modifier keys

# gets the code for a C-<key> that is pressed
inline ctrl_key (k)
    (& k CTRL_KEY_BITMASK)

fn editor_read_key ()

    local input : i8

    let read-result = (read_char input)

    read-result

    # TODO
    # # handle control characters
    # let is_control_char = (C:ctype.extern.patched_iscntrl input)

    # # echo the correct representation of the input to stdout
    # if (is_control_char == 0)
    #     print (util.char_encode input)


fn editor_dispatch_keypress ()

    let input-key = (editor_read_key)

    print input-key

    switch input-key

    case (ctrl_key (char32 "q"))
        EditorActions.Terminate

    default
        EditorActions.Nothing

fn perform_editor_action (action)

    switch action

    case EditorActions.Terminate
        print "terminating"
        action

    default
        action

fn main ()

    print "Welcome to skilo"
    print "Press any key"
    print "Press 'C-q' to quit"

    local terminate? = false
    local error? = false

    # turns rawmode on and shuts it down at the end of the surrounding function
    # try
    local orig-termios-state = (termios.get_termios_state)
    defer termios.disable_term_rawmode orig-termios-state
    (termios.enable_term_rawmode TERMIOS_VTIME)
    # patch the print statement
    let _print = print
    let print = termios.raw_print!

    local k : i64
    # local action : EditorActions

    while true
        # k = (editor_read_key)

        let action = (editor_dispatch_keypress)

        # (perform_editor_action action)


        # elseif (is_control_char == 2)
        #     print (tostring input)

        # q for the terminate condition, do this first so we can always exit
        # if (input == (ctrl_key (char32 "q")))
        #     print  "terminating"
        #     terminate? = true

# main;

locals;
