# !/usr/bin/python
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# -*- coding: UTF-8 -*-
import yaml, os, time


class Metadata(yaml.YAMLObject):
    yaml_tag = u'!Metadata'

    def __init__(self, name):
        self.name = name


class Spec(yaml.YAMLObject):
    yaml_tag = u'!Spec'

    def __init__(self, hosts, http):
        self.hosts = hosts
        self.http = http


class Http(yaml.YAMLObject):
    yaml_tag = u'!Http'

    def __init__(self, routes):
        self.route = routes


class Route(yaml.YAMLObject):
    yaml_tag = u'!Route'

    def __init__(self, destination, weight):
        self.destination = destination
        self.weight = weight


class Destination(yaml.YAMLObject):
    yaml_tag = u'!Destination'

    def __init__(self, svcName, subset):
        self.host = svcName
        self.subset = subset


class VirtualService(yaml.YAMLObject):
    yaml_tag = u'!VirtualService'

    def __init__(self, svcName, sw):
        self.apiVersion = 'networking.istio.io/v1alpha3'
        self.kind = 'VirtualService'
        self.metadata = Metadata(svcName)
        routes = []
        for subset, weight in sw.items():
            dest = Destination(svcName, subset)
            route = Route(dest, weight)
            routes.append(route)
        http = Http(routes)
        hosts = [svcName]
        self.spec = Spec(hosts, http)


def noop(self, *args, **kw):
    pass


yaml.emitter.Emitter.process_tag = noop
dict = {'v1': 100, 'v2': 0}
while True:
    vs = VirtualService('ts-voucher-service', dict)
    f = open(r'virtual-services-fault.yaml', 'w')
    yaml.dump(vs, f)
    (status, output) = os.system('kubectl apply -f virtual-services-fault.yaml')
    #status = 0
    if status == 0:
        time.sleep(5)
        if dict['v1'] > 0:
            dict['v1'] = dict['v1'] - 10
            dict['v2'] = dict['v2'] + 10
        elif dict['v1'] == 0:
            dict['v1'] = 100
            dict['v2'] = 0
        else:
            pass
    else:
        raise RuntimeError('output')
