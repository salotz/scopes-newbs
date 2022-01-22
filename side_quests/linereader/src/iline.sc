using import struct
using import String

let ILINE_STARTING_BUFFER_SIZE = 4096

let STDIN_FILENO = 0
let STDOUT_FILENO = 1
let STDERR_FILENO = 2

struct ilineState
    ifd : i32
    ofd : i32
    buf : u32
    buflen : u64
    prompt : u64
    plen : u64
    pos : u64
    oldpos : u64
    len : u64
    cols : i32



fn enable_term_rawmode ()
    # TODO: set the terminal to raw mode

fn disable_term_rawmode ()
    # TODO: set the terminal to raw mode

fn get_columns (stdin_fd stdout_fd)

    

fn iline_edit (prompt buffer stdin_fd stdout_fd)

    let cols = (get_columns stdin_fd stdout_fd)

    # initialize the state of the line reader
    local state =
        ilineState
            (ifd = stdin_fd)
            (ofd = stdout_fd)
            (buf = buffer)
            (buflen = (countof buffer))
            (prompt = prompt)
            (plen = (countof prompt))
            (pos = 0)
            (oldpos = 0)
            (len = 0)
            (cols = cols)

fn iline_raw (prompt buffer)
    local count : i32

    (enable_term_rawmode)

    count = (iline_edit prompt buffer STDIN_FILENO STDOUT_FILENO)

    (disable_term_rawmode)

    io-write! "\n"

    return count

fn iline (prompt)
    local count : i32
    local buffer = (String ILINE_STARTING_BUFFER_SIZE)

    count = (iline_raw prompt buffer)

    if (count == -1)
        return none
    else
        return (copy buffer)


