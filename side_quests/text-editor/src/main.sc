using import struct
using import String
import C.string
using import UTF-8

fn import_c_stdlib (header-name)

    let header = (include header-name)

    ..
        header.extern
        header.typedef
        header.define
        header.const
        header.struct

let C.termios = (import_c_stdlib "termios.h")
let C.termios = (import_c_stdlib "unistd.h")

# just define the FILNOs ourselves instead of loading from unistd.h
let STDIN_FILENO = 0
let STDOUT_FILENO = 1
let STDERR_FILENO = 2

let C.termios =
    (include "termios.h") . extern

let C.unistd =
    (include "unistd.h") . extern

# fn enable_term_rawmode ()
    

# fn disable_term_rawmode ()


fn read_char (input)
    (C.unistd.read STDIN_FILENO (& input) 1)

# fn rawstring_to_String (rstr)
#     (String rstr (C.string.strlen rstr))

fn main ()

    local input : i8

    local read-result = (read_char input)

    local terminate = false
    while (
        (read-result == 1) and
        (not terminate)
    )

        if (input == (char32 "q"))
            print "terminating"
            terminate = true

        ### Read input for next iteration
        if (not terminate)
            read-result = (read_char input)



main;
