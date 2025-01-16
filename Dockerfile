# Use CentOS Stream 9 container image from quay.io as the base
FROM quay.io/centos/centos:stream9

RUN dnf -y install git

RUN yum install -y maven