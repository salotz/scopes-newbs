# this loads the site specific packaging information
let pkg = (import ..pkg)

let header =
    include
        "linenoise.h"
        options
            # "-v"
            .. "-I" pkg.include-path

let object-path = (.. pkg.lib-path "/linenoise.o")
load-object object-path

## dump the namespaces for the differnt header things
let namespace =
    ..
        header.extern
        header.typedef
        header.define
        header.const
        header.struct


do
    let
        header

    .. namespace (locals)
