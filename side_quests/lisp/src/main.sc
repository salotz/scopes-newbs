using import String
using import Array
import C.stdio

let linenoise = (import .linenoise)

let input-buffer-size = 2048

fn main ()

    local input-buffer = ((array i8 input-buffer-size))
    # local input = ((Array i8 input-buffer-size))

    print "Language: 0.0.0.0.1"
    print "Press Ctrl-c to Exit\n"

    while true
        # the prompt
        (C.stdio.fputs "> " C.stdio.stdout)

        # receive input
        (C.stdio.fgets input-buffer input-buffer-size C.stdio.stdin)

        # convert the input to a string
        let input = (String (& input-buffer) input-buffer-size)

        print "==>" input

main;
