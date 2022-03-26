'bind-symbols __env

    module-search-path =
        cons
            # the current module in development
            .. module-dir "/src/?.sc"
            .. module-dir "/src/?/init.sc"
            __env.module-search-path

    include-search-path = __env.include-search-path

    library-search-path = __env.library-search-path
