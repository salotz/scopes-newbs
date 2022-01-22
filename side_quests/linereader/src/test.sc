import .iline

let prompt = ">"
let response-prefix = "==>"

loop (line = (iline.edit_line prompt))
    if line == null
        break
    else
        io-write! (.. response-prefix " " line "\n")
