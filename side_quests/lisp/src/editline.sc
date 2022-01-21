# this loads the site specific packaging information
let pkg = (import ..pkg)

let header =
    include
        "editline.h"
        options
            # "-v"
            .. "-I" pkg.include-path

let lib-path = (.. pkg.lib-path "/editline.so")

load-library lib-path

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
