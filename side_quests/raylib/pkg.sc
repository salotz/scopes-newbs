
let include-path =
    .. module-dir "/" ".spack-env/view/include"

let lib-path =
    .. module-dir "/" ".spack-env/view/lib"


do
    let include-path lib-path
    locals;
