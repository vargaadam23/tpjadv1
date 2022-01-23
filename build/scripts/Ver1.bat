@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Ver1 startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and VER1_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Ver1-1.0-SNAPSHOT.jar;%APP_HOME%\lib\spring-boot-starter-web-2.1.3.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-json-2.1.3.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-2.1.3.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-log4j2-2.1.3.RELEASE.jar;%APP_HOME%\lib\tapestry-webresources-5.7.3.jar;%APP_HOME%\lib\tapestry-core-5.7.3.jar;%APP_HOME%\lib\log4j-slf4j-impl-2.11.2.jar;%APP_HOME%\lib\yasson-1.0.1.jar;%APP_HOME%\lib\javax.json-1.1.2.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.9.8.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.9.8.jar;%APP_HOME%\lib\jackson-module-parameter-names-2.9.8.jar;%APP_HOME%\lib\jackson-databind-2.9.8.jar;%APP_HOME%\lib\jackson-dataformat-yaml-2.9.8.jar;%APP_HOME%\lib\jackson-annotations-2.9.0.jar;%APP_HOME%\lib\jackson-core-2.9.8.jar;%APP_HOME%\lib\log4j-core-2.11.2.jar;%APP_HOME%\lib\log4j-jul-2.11.2.jar;%APP_HOME%\lib\log4j-api-2.11.2.jar;%APP_HOME%\lib\spring-webmvc-5.1.5.RELEASE.jar;%APP_HOME%\lib\spring-boot-autoconfigure-2.1.3.RELEASE.jar;%APP_HOME%\lib\spring-boot-2.1.3.RELEASE.jar;%APP_HOME%\lib\spring-context-5.1.5.RELEASE.jar;%APP_HOME%\lib\spring-aop-5.1.5.RELEASE.jar;%APP_HOME%\lib\spring-web-5.1.5.RELEASE.jar;%APP_HOME%\lib\spring-beans-5.1.5.RELEASE.jar;%APP_HOME%\lib\spring-expression-5.1.5.RELEASE.jar;%APP_HOME%\lib\spring-core-5.1.5.RELEASE.jar;%APP_HOME%\lib\spring-jcl-5.1.5.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-tomcat-2.1.3.RELEASE.jar;%APP_HOME%\lib\hibernate-validator-6.0.14.Final.jar;%APP_HOME%\lib\classmate-1.4.0.jar;%APP_HOME%\lib\closure-compiler-unshaded-v20200504.jar;%APP_HOME%\lib\gson-2.8.5.jar;%APP_HOME%\lib\jaxws-rt-2.3.2.jar;%APP_HOME%\lib\saaj-impl-1.5.1.jar;%APP_HOME%\lib\tapestry-http-5.7.3.jar;%APP_HOME%\lib\commons-codec-1.11.jar;%APP_HOME%\lib\jaxws-api-2.3.1.jar;%APP_HOME%\lib\jaxb-api-2.3.1.jar;%APP_HOME%\lib\javax.activation-api-1.2.0.jar;%APP_HOME%\lib\tapestry-ioc-5.7.3.jar;%APP_HOME%\lib\javax.annotation-api-1.3.2.jar;%APP_HOME%\lib\javax.json-api-1.1.4.jar;%APP_HOME%\lib\javax.json.bind-api-1.0.jar;%APP_HOME%\lib\validation-api-2.0.1.Final.jar;%APP_HOME%\lib\tomcat-embed-websocket-9.0.16.jar;%APP_HOME%\lib\tomcat-embed-core-9.0.16.jar;%APP_HOME%\lib\tomcat-annotations-api-9.0.16.jar;%APP_HOME%\lib\tomcat-embed-el-9.0.16.jar;%APP_HOME%\lib\jaxb-runtime-2.3.2.jar;%APP_HOME%\lib\jboss-logging-3.3.2.Final.jar;%APP_HOME%\lib\mimepull-1.9.11.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.25.jar;%APP_HOME%\lib\beanmodel-5.7.3.jar;%APP_HOME%\lib\commons-5.7.3.jar;%APP_HOME%\lib\plastic-5.7.3.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\snakeyaml-1.23.jar;%APP_HOME%\lib\tapestry-json-5.7.3.jar;%APP_HOME%\lib\jakarta.xml.ws-api-2.3.2.jar;%APP_HOME%\lib\jakarta.annotation-api-1.3.4.jar;%APP_HOME%\lib\streambuffer-1.5.7.jar;%APP_HOME%\lib\stax-ex-1.8.1.jar;%APP_HOME%\lib\jakarta.xml.bind-api-2.3.2.jar;%APP_HOME%\lib\less4j-1.12.0.jar;%APP_HOME%\lib\rhino-1.7.7.2.jar;%APP_HOME%\lib\cdi-api-2.0.jar;%APP_HOME%\lib\tapestry-func-5.7.3.jar;%APP_HOME%\lib\tapestry5-annotations-5.7.3.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\antlr-runtime-3.5.2.jar;%APP_HOME%\lib\istack-commons-runtime-3.0.8.jar;%APP_HOME%\lib\jakarta.activation-api-1.2.1.jar;%APP_HOME%\lib\jakarta.xml.soap-api-1.4.1.jar;%APP_HOME%\lib\jakarta.jws-api-1.1.1.jar;%APP_HOME%\lib\policy-2.7.6.jar;%APP_HOME%\lib\gmbal-4.0.0.jar;%APP_HOME%\lib\FastInfoset-1.2.16.jar;%APP_HOME%\lib\ha-api-3.1.12.jar;%APP_HOME%\lib\woodstox-core-5.1.0.jar;%APP_HOME%\lib\stax2-api-4.1.jar;%APP_HOME%\lib\txw2-2.3.2.jar;%APP_HOME%\lib\javax.xml.soap-api-1.4.0.jar;%APP_HOME%\lib\commons-io-2.3.jar;%APP_HOME%\lib\commons-beanutils-1.8.3.jar;%APP_HOME%\lib\json-20090211.jar;%APP_HOME%\lib\protobuf-java-3.11.1.jar;%APP_HOME%\lib\closure-compiler-externs-v20200504.jar;%APP_HOME%\lib\args4j-2.0.26.jar;%APP_HOME%\lib\guava-25.1-jre.jar;%APP_HOME%\lib\error_prone_annotations-2.3.1.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\elemental2-core-1.0.0-RC1.jar;%APP_HOME%\lib\base-1.0.0.jar;%APP_HOME%\lib\jsinterop-annotations-1.0.2.jar;%APP_HOME%\lib\re2j-1.3.jar;%APP_HOME%\lib\javax.el-api-3.0.0.jar;%APP_HOME%\lib\javax.interceptor-api-1.2.jar;%APP_HOME%\lib\management-api-3.2.1.jar;%APP_HOME%\lib\pfl-tf-tools-4.0.1.jar;%APP_HOME%\lib\pfl-basic-tools-4.0.1.jar;%APP_HOME%\lib\pfl-tf-4.0.1.jar;%APP_HOME%\lib\pfl-dynamic-4.0.1.jar;%APP_HOME%\lib\pfl-basic-4.0.1.jar;%APP_HOME%\lib\commons-logging-1.1.1.jar;%APP_HOME%\lib\checker-qual-2.0.0.jar;%APP_HOME%\lib\j2objc-annotations-1.1.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.14.jar;%APP_HOME%\lib\pfl-asm-4.0.1.jar


@rem Execute Ver1
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %VER1_OPTS%  -classpath "%CLASSPATH%" org.tpjad.App %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable VER1_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%VER1_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
