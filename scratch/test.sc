using import Array

let array_size = 3

let things = ((Array string array_size) "a" "b" "c")

# print (things @ 0)

loop (idx = 0)

    if (idx < array_size)

        print (things @ idx)

        idx + 1
    else
        break idx

;
