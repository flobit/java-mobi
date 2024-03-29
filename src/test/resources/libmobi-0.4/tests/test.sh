#!/bin/bash
# test.sh
# Copyright (c) 2015 Bartek Fabiszewski
# http://www.fabiszewski.net
#
# This file is part of libmobi.
# Licensed under LGPL, either version 3, or any later.
# See <http://www.gnu.org/licenses/>

# Usage: test.sh filename <optional parameters passed to mobitool>

if [[ $# < 1 ]]; then
    die "Wrong number of input parameters: $#" 1
fi

testfile="$1"
testfile_md5=
md5file_markup=
md5file_rawml=
basefile="${testfile##*/}"
shift
options="$@"
markup_dir="${basefile%.*}_markup"
tmp_dir="tmp"
rawml_file="${basefile%.*}.rawml"
md5prog="md5sum -t"
mobitool="../tools/mobitool"
if [[ "xno" == "xyes" ]]; then
    runfile=${testfile//\//\\}
else
    runfile=${testfile}
fi
pid=
do_md5=1
skip=77

log() {
    echo
    echo "[ $1 ]"
    echo
}

die() {
    log "${testfile}: $1"
    exit $2
}

[[ -f "${testfile}" ]] || die "Missing sample file: ${testfile}" $skip
[[ -x "${mobitool}" ]] || die "Missing mobitool" $skip

if [[ "${basefile}" == 'drm_pid'* ]]; then
    pid=${basefile:7:10}
    if [[ "${pid}" =~ [^a-zA-Z0-9] ]]; then
        log "Ignoring incorrect pid ${pid}"
    else
        options="${options} -p ${pid}"
        log "Using pid ${pid}"
    fi

fi

if [[ -z "${md5prog}" ]]; then
    do_md5=0
    log "Missing md5 tool: ${md5prog}, will skip md5 tests"
else
    testfile_md5=$(${md5prog} "${testfile}")
    testfile_md5=${testfile_md5%% *}
    md5file_markup="md5/${testfile_md5}_markup.md5"
    md5file_rawml="md5/${testfile_md5}_rawml.md5"
fi

# create tmp dir
mkdir -p "${tmp_dir}"

# recreate source files
rm -rf "${tmp_dir}/${markup_dir}"
log "Running ${mobitool} -o \"${tmp_dir}\" -s ${options} \"${runfile}\""
${mobitool} -o "${tmp_dir}" -s ${options} "${runfile}" || die "Recreating source failed, mobitool error ($?)" $?
[[ -d "${tmp_dir}/${markup_dir}" ]] || die "Recreating source failed" 1

# verify checksums
if [[ ${do_md5} ]]; then
    if [[ -f "${md5file_markup}" ]]; then
        (
            cd "${tmp_dir}/${markup_dir}" || die "Could not change directory to ${tmp_dir}/${markup_dir}" $?
            rm -f "../${testfile_md5}.md5"
            ${md5prog} * > "../${testfile_md5}.md5"
            diff -w "../${testfile_md5}.md5" "../../${md5file_markup}" || die "Wrong md5 checksum in ${markup_dir}" $?
            rm -f "../${testfile_md5}.md5"
        ) || exit $?
        log "Checksum correct"
    else
        log "No checksums file: ${md5file_markup}, skipping md5 test"
    fi
fi
rm -rf "${tmp_dir}/${markup_dir}"

# dump rawml
rm -f "${tmp_dir}/${rawml_file}"
log "Running ${mobitool} -o \"${tmp_dir}\" -d ${options} \"${runfile}\""
${mobitool} -o "${tmp_dir}" -d ${options} "${runfile}" || die "Dumping rawml failed, mobitool error ($?)" $?
[[ -f "${tmp_dir}/${rawml_file}" ]] || die "Dumping rawml failed" 1

# verify checksum
if [[ ${do_md5} ]]; then
    if [[ -f "${md5file_rawml}" ]]; then
        md5_1=$(cat ${md5file_rawml})
        md5_2=$(${md5prog} "${tmp_dir}/${rawml_file}")
        [[ "${md5_1%% *}" == "${md5_2%% *}" ]] || die "Wrong md5 checksum for ${rawml_file} (${md5_1%% *} != ${md5_2%% *})" 1
        log "Checksum correct"
    else
        log "No checksums file: ${md5file_rawml}, skipping md5 test"
    fi
fi
rm -f "${tmp_dir}/${rawml_file}"

exit 0
