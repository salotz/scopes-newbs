

let glfw = (import .glfw)
let gl = (import .glad)


# load the libraries

let glfw-lib-path = (.. module-dir "/../_guix/dev/dev/lib/libglfw.so")
let glad-lib-path = (.. module-dir "/../cdeps/glad/src/glad.o")

load-library glfw-lib-path
load-object glad-lib-path

run-stage;


fn framebuffer_size_callback (window width height)
    print "hello"

fn process_input (window)

    # if the escape key was pressed close the window
    if ((glfw.GetKey window glfw.GLFW_KEY_ESCAPE) == glfw.GLFW_PRESS)
        (glfw.SetWindowShouldClose window true)


### Set up the GLFW window
glfw.Init;

(glfw.WindowHint glfw.GLFW_CONTEXT_VERSION_MAJOR 3)
(glfw.WindowHint glfw.GLFW_CONTEXT_VERSION_MINOR 3)
(glfw.WindowHint glfw.GLFW_OPENGL_PROFILE glfw.GLFW_OPENGL_CORE_PROFILE)

let window = (glfw.CreateWindow 800 600 "Hello World" null null)

if (window == null)
    print "Failed to create GLFW window"
    glfw.Terminate;

(glfw.MakeContextCurrent window)
(glfw.SetFramebufferSizeCallback window framebuffer_size_callback)

## Load the GL function pointers
let gl_load_status = (gl.LoadGL)
if (gl_load_status == 0)
    print "Failed to initialize OpenGL"
    assert false

# NOTE: weird glad function pointer stuff not needed I think
# if (not glad.LoadGLLoader)

let background_color = '(
    0.2:f32
    0.4:f32
    0.3:f32
    1.0:f32
)

### The Main Loop
let terminate = false
while (
        (not (glfw.WindowShouldClose window)) and
        (not terminate)
    )

    ### Pre Render Tasks
    (process_input window)

    ### Rendering

    ## set the color for the background
    gl.ClearColor (unpack background_color)

    (gl.Clear gl.GL_COLOR_BUFFER_BIT)

    ### Post Render Tasks
    if (not terminate)
        (glfw.SwapBuffers window)
        glfw.PollEvents;

### Termination Routines on normal exit
glfw.Terminate;

