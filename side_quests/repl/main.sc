inline run-repl (scope)
    """"Will start up the repl with all of the current locals injected
        into the environment. Note this won't work if you import it.

    let final_scope = (.. (globals) scope)

    if main-module?
        run-stage;

    if main-module?
        using import console
        read-eval-print-loop final_scope false

let a = 3
fn foo ()
    print "foo"

run-repl (locals)
