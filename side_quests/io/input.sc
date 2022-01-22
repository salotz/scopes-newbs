import C.stdio
import C.string
using import String

# display a prompt
# (C.stdio.fputs "> " C.stdio.stdout)

# allocate a C-array for collecting input


# local rstr = (rawstring 2048)

# print (typeof rstr)

local chararr = ((array i8 2048))

print (typeof chararr)

(String &chararr 2048)

# (String rstr 2048)

# get input from stdin
# (C.stdio.fgets input 2048 C.stdio.stdin)

# print (C.string.strlen input)



# convert input to a string
# (String input (C.string.strlen input))
