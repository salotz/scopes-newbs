using import struct
using import Option

let C:termios = (include "termios.h")
let C:unistd = (include "unistd.h")

# we hardcode VMIN so that the terminal always returns
let
    VMIN = 0

struct TermiosError
    error-code : i32
    termios-state : C:termios.struct.termios
    attr-code : (Option i32)
    file-id : i32

fn tcsetattr (file-id attr-code termios-state)

    let result =
        C:termios.extern.tcsetattr
            file-id
            attr-code
            (& termios-state)

    if (result == -1)
        # raise
        #     TermiosError
        #         result
        #         termios-state
        #         attr-code
        #         file-id

    return termios-state

fn tcgetattr (file-id)

    local termios-state : C:termios.struct.termios

    let result =
        C:termios.extern.tcgetattr
            file-id
            (& termios-state)

    if (result == -1)
        # raise
        #     TermiosError
        #         result
        #         termios-state
        #         none
        #         file-id

    return termios-state

fn get_termios_state ()

    local termios-state = (tcgetattr C:unistd.define.STDIN_FILENO)

    return termios-state


fn disable_term_rawmode (original_termios)

    tcsetattr
        C:unistd.define.STDIN_FILENO
        C:termios.define.TCSAFLUSH
        original_termios

    ;

fn enable_term_rawmode (vtime)

    # the termios state when we started rawmode
    local orig_termios = (get_termios_state)

    # a new one to mutate for rawmode
    local raw_termios = (copy orig_termios)

    # set the necessary flags for the different categories
    raw_termios.c_iflag =
        ~
            |
                (C:termios.define.ICRNL as u32)
                (C:termios.define.IXON as u32)
                (C:termios.define.BRKINT as u32)
                (C:termios.define.INPCK as u32)
                (C:termios.define.ISTRIP as u32)

    raw_termios.c_oflag =
        ~
            |
                (C:termios.define.OPOST as u32)

    raw_termios.c_cflag =
        ~
            |
                (C:termios.define.CS8 as u32)

    raw_termios.c_lflag =
        ~
            |
                (C:termios.define.ECHO as u32)
                (C:termios.define.ICANON as u32)
                (C:termios.define.IEXTEN as u32)
                (C:termios.define.ISIG as u32)

    # set the timeout for reading
    (raw_termios.c_cc @ C:termios.define.VMIN) = VMIN
    (raw_termios.c_cc @ C:termios.define.VTIME) = vtime

    C:termios.extern.tcsetattr
        C:unistd.define.STDIN_FILENO
        C:termios.define.TCSAFLUSH
        (& raw_termios)

    orig_termios

let RAW-NEWLINE = "\r\n"

inline raw_print! (str)
    io-write! (.. str RAW-NEWLINE)


## Macros

# set and forget
sugar ~do_rawmode (vtime)

    let
        raw_print!
        vtime
        get_termios_state
        disable_term_rawmode
        enable_term_rawmode

    let result =
        qq
            # patch print to use the correct newlines
            let _print = print
            let print = [raw_print!]

            local orig-termios-state = ([get_termios_state])
            defer [disable_term_rawmode] orig-termios-state
            ([enable_term_rawmode] [vtime])

    result

# context manager
sugar term_rawmode: (vtime body...)

    let raw_print! vtime

    qq
        do
            # patch print to use the correct newlines
            let _print = print
            let print = [raw_print!]

            local orig-termios-state = (termios.get_termios_state)
            defer termios.disable_term_rawmode orig-termios-state
            (termios.enable_term_rawmode [vtime])
            unquote-splice body...

let macros =
    'bind-symbols (Scope)
        ~do_rawmode = ~do_rawmode
        term_rawmode: = term_rawmode:


do
    let
        TermiosError
        tcsetattr
        tcgetattr
        get_termios_state
        disable_term_rawmode
        enable_term_rawmode
        raw_print!
        RAW-NEWLINE
        macros
    (locals)
