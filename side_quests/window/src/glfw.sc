using import .ffi-helpers

# do
#     let filter-scope
#     locals;

let header =
    include
        "GLFW/glfw3.h"
        options
            # "-v"
            .. "-I" module-dir "/../_guix/dev/dev/include"

# for each of the parts of the included module we filter based on a
# prefix and bind these to the namespaces without them. 

# E.g. instead of writing glfw.glfwInit; you can write just glfw.Init;
let glfw-extern = (filter-scope header.extern "^glfw")
let glfw-typedef = (filter-scope header.typedef "^GLFW")
let glfw-define = (filter-scope header.define "^(?=GLFW_)")

# .. glfw-extern glfw-typedef glfw-define
# load the header as a module
# let glfw =
#     include
#         "GLFW/glfw3.h"
#         options
#             # "-v"
#             .. "-I" module-dir "/../_guix/dev/dev/include"

.. glfw-extern glfw-typedef glfw-define
