inline filter-scope (scope pattern)
    """"For a scope match the pattern to a symbol prefix and remove it from the outputted
        scope symbol, useful for stripping namespace prefixes from C libraries that
        are unnecessary in scopes since we have explicit Scopes
    fold (scope = (Scope)) for k v in scope
        let name = (k as Symbol as string)
        let match? start end = ('match? pattern name)
        if match?
            'bind scope (Symbol (rslice name end)) v
        else
            scope

do
    let filter-scope
    locals;
