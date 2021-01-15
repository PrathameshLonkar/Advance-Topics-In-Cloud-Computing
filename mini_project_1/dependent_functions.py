import unshare
import argparse
import os
import sys
from cgroups import Cgroup
import subprocess

def uts_namespace(args):
    unshare.unshare(unshare.CLONE_NEWUTS)
    replace_hostname(args)
    pass

def replace_hostname(args):
    hostname= "hostname " + args.hostname
    os.system(hostname)
    pass

def net_namespace(args):
    unshare.unshare(unshare.CLONE_NEWNET)
    replace_ip(args)
    pass

def replace_ip(args):
    os.system("ip netns add myns1")
    os.system("modprobe dummy")
    os.system("ip link add dummy1 type dummy")
    os.system("ip link set name eth1 dev dummy1")
    os.system("ifconfig eth1 " + args.ip_addr)
    pass

def mnt_namespace(args):
    unshare.unshare(unshare.CLONE_NEWNS)
    pass

def pid_namespace(args):
    unshare.unshare(unshare.CLONE_NEWPID)
    pass

def cpu_cgroup(args):
    pass
    


def mem_cgroup(args):
    cgroup_fun(args)
    pass

def cgroup_fun(args):
    control_group = Cgroup("memory_cgroup")
    control_group.set_memory_limit(args.mem_size)
    control_group.add(os.getpid())
    pass

def mount_root(args):
    dir_path = args.root_path
    os.chdir(dir_path);
    os.chroot('.')
    os.system("mount -t proc proc /proc")
    os.execle('/bin/bash', '/bin/bash', os.environ)
    pass