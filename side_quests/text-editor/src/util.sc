using import itertools
import UTF-8

fn encode (arr)
    ->>
        arr
        UTF-8.encoder
        string.collector ((countof arr) * (sizeof i32))

fn char_encode (ch)
    local arr = (arrayof i32 ch)
    (encode arr)

do
    let
        char_encode
        encode
    locals;
