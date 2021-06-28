#!/bin/bash

# 3-Clause BSD License
#
# Copyright (c) 2009, Boxed Ice <hello@boxedice.com>
# Copyright (c) 2010-2016, Datadog <info@datadoghq.com>
# Copyright (c) 2020-present, Akita Software <info@akitasoftware.com>
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
#     * Redistributions of source code must retain the above copyright notice,
#       this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above copyright notice,
#       this list of conditions and the following disclaimer in the documentation
#       and/or other materials provided with the distribution.
#     * Neither the name of the copyright holder nor the names of its contributors
#       may be used to endorse or promote products derived from this software
#       without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
# FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
# DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
# SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
# CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
# OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

# Script adapted from
# https://github.com/DataDog/dd-agent/blob/master/packaging/datadog-agent/source/install_agent.sh

set -e

function on_error() {
    printf "\033[31m${ERROR_MESSAGE}
It looks like you hit an issue when trying to install the Akita CLI.

A troubleshooting guide is available at:

    https://docs.akita.software/docs/install-akita-client

If you're still having problems, please send an email to
support@akitasoftware.com.\n\033[0m\n"
}
trap on_error ERR

# OS/Distro Detection
# Try lsb_release, fallback with /etc/issue then uname command
KNOWN_DISTRIBUTION="(Debian|Ubuntu|RedHat|CentOS|Amazon)"
DISTRIBUTION=$(lsb_release -d 2>/dev/null | grep -Eo $KNOWN_DISTRIBUTION  || grep -Eo $KNOWN_DISTRIBUTION /etc/issue 2>/dev/null || grep -Eo $KNOWN_DISTRIBUTION /etc/Eos-release 2>/dev/null || grep -m1 -Eo $KNOWN_DISTRIBUTION /etc/os-release 2>/dev/null || uname -s)

if [ "$DISTRIBUTION" = "Darwin" ]; then
    printf "\033[31mERROR
This script does not support installing on the Mac.

Please use the instructions available at
https://docs.akita.software/docs/install-akita-client#section-mac-os-installation.
*****\n\033[0m\n"
    exit 1;
elif [ -f /etc/debian_version ] || [ "$DISTRIBUTION" == "Debian" ] || [ "$DISTRIBUTION" == "Ubuntu" ]; then
    OS="Debian"
elif [ -f /etc/redhat-release ] || [ "$DISTRIBUTION" == "RedHat" ] || [ "$DISTRIBUTION" == "CentOS" ] || [ "$DISTRIBUTION" == "Amazon" ]; then
    OS="RedHat"
# Some newer distros like Amazon may not have a redhat-release file
elif [ -f /etc/system-release ] || [ "$DISTRIBUTION" == "Amazon" ]; then
    OS="RedHat"
fi

# Root user detection
if [ "$(echo "$UID")" = "0" ]; then
    sudo_cmd=''
else
    sudo_cmd='sudo'
fi

# Platform detection
# Currently we only support amd64/x86-64
UNAME_M=$(uname -m)
if [ "$UNAME_M" == "i686" ] || [ "$UNAME_M" == "i386" ] || [ "$UNAME_M" == "x86" ]; then
    ARCHI="i386"
    printf "\033[31mERROR
${ARCHI} not supported. Please run on x86-64 platform.
*****\n\033[0m\n"
    exit 1;
elif [ "$UNAME_M" == "aarch64" ]; then
    ARCHI="aarch64"
    printf "\033[31mERROR
${ARCHI} not supported. Please run on x86-64 platform.
*****\n\033[0m\n"
    exit 1;
else
    ARCHI="x86_64"
fi

printf "\033[34m\n* Installing prerequisite utilities \n\033[0m\n"
$sudo_cmd apt-get update || printf "\033[31m'apt-get update' failed, the script will not install the latest version of prerequisites.\033[0m\n"
$sudo_cmd apt-get install -y apt-transport-https curl gnupg

printf "\033[34m\n* Installing APT package sources for Akita\n\033[0m\n"
$sudo_cmd sh -c "echo 'deb [arch=amd64] https://apt.releases.akita.software/ stable main' > /etc/apt/sources.list.d/akita.list"
curl -f https://releases.akita.software/keys/akita-apt.public | $sudo_cmd apt-key add -

printf "\033[34m\n* Installing the Akita CLI package\n\033[0m\n"
ERROR_MESSAGE="ERROR
Failed to update the sources after adding the Akita repository.
This may be due to any of the configured APT sources failing -
see the logs above to determine the cause.
If the failing repository is Akita, please contact Akita support.
*****
"
$sudo_cmd apt-get update -o Dir::Etc::sourcelist="sources.list.d/akita.list" -o Dir::Etc::sourceparts="-" -o APT::Get::List-Cleanup="0"

ERROR_MESSAGE="ERROR
Failed to install the Akita package, sometimes it may be
due to another APT source failing. See the logs above to
determine the cause.
If the cause is unclear, please contact Akita support.
*****
"
$sudo_cmd apt-get install -y --force-yes akita-cli
ERROR_MESSAGE=""
