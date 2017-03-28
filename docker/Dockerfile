############################################################
# Dockerfile to build EWP reference connector container images
# Based on Java
############################################################

FROM java:8-jre

MAINTAINER Mikael Berglund with props to https://github.com/tomitribe/docker-tomee

ENV PATH /usr/local/tomee/bin:$PATH
RUN mkdir -p /usr/local/tomee

WORKDIR /usr/local/tomee

# gpg: Signature made Sun Nov  6 19:40:20 2016 CET using DSA key ID 2678363C
# gpg: Good signature from "Romain Manni-Bucau <rmannibucau@apache.org>"
RUN gpg --keyserver pgpkeys.mit.edu --recv-key 2678363C

RUN set -x \
	&& curl -fSL https://dist.apache.org/repos/dist/release/tomee/tomee-7.0.2/apache-tomee-7.0.2-plus.tar.gz.asc -o tomee.tar.gz.asc \
	&& curl -fSL http://apache.rediris.es/tomee/tomee-7.0.2/apache-tomee-7.0.2-plus.tar.gz -o tomee.tar.gz \
	&& gpg --batch --verify tomee.tar.gz.asc tomee.tar.gz \
	&& tar -zxf tomee.tar.gz \
	&& mv apache-tomee-plus-7.0.2/* /usr/local/tomee \
	&& rm -Rf apache-tomee-plus-7.0.2 \
	&& rm bin/*.bat \
	&& rm tomee.tar.gz*

ADD ewp-reference-connector-1.0-SNAPSHOT.war /tmp
RUN unzip /tmp/ewp-reference-connector-1.0-SNAPSHOT.war -d /usr/local/tomee/webapps/ewp

EXPOSE 8080
CMD ["catalina.sh", "run"]