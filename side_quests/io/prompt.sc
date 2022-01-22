using import String
import C.stdio

let input-prompt = ">"
let result-prompt = "==>"

# display a prompt
io-write! (.. input-prompt " ")

# allocate a C-array for collecting input
local input = ((array i8 2048))

# get input from stdin
(C.stdio.fgets input 2048 C.stdio.stdin)

# io-write! "\n"

# then convert to a string
let input-str = (String (& input) (countof input))

io-write! (.. result-prompt " ")
io-write! (input-str as string)
