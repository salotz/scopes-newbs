
from pathlib import Path

DOIT_CONFIG = {'action_string_formatting': 'new'}

TANGLED_DIR = Path("_bin")

def get_tangled_sources():
    return list(TANGLED_DIR.glob("*.sc"))

def task_tangle():

    return {
        'file_dep' : [
            'README.org',
        ],
        'clean' : True,

        'targets' : get_tangled_sources() + [
            '_bin',
        ],

        'actions' : [
            "mkdir -p _bin",
            "emacs -Q --batch -l org {dependencies} -f org-babel-tangle"
        ]
    }


def task_test():

    srcs = get_tangled_sources()

    return {

        # we always just have to run the tangle since we can't know if
        # they are all tangled or not

        # 'file_dep' : srcs,
        'task_dep' : ['tangle'],

        # always run this
        'uptodate' : [False],

        # show the output
        'verbosity' : 2,

        # run scopes on each file
        'actions' : [
            f"echo {src}; scopes {src}"
            for src
            in srcs
        ],
    }
