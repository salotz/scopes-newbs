using import .ffi-helpers

let header =
    include
        "glad/glad.h"
        options
            .. "-I" module-dir "/../cdeps/glad/include"

let glad-extern = (filter-scope header.extern "^gl(?=[A-Z])")
let glad-define = (filter-scope header.define "^((?=GL_)|gl(?=[A-Z]))")

.. glad-extern glad-define
    do
        let LoadGL = header.extern.gladLoadGL
        locals;
