# Deployment at Sonatype #

  * [Sonatype docs](https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide)
  * [Releasing a project to Maven Central repository via Sonatype](http://www.jroller.com/holy/entry/releasing_a_project_to_maven)
  * [PGP PUBLIC KEY](http://pgp.mit.edu:11371/pks/lookup?op=get&search=0x99EAEDDECFCF8216)

## Prerequisites ##
  * Maven 2.2.1
  * Mercurial 1.4 (for `[auth]` section to work)

  * http://www.singhvishwajeet.com/2009/09/16/solving-the-build-error-python-h-no-such-file-or-directory-on-ubuntu/
  * http://mercurial.aragost.com/kick-start/basic.html
  * https://help.ubuntu.com/community/Mercurial

## Maven configuration ($HOME/.m2/settings.xml) ##
```
<settings>
    <proxies>
        <proxy>
            <id>optional</id>
            <active>true</active>
            <protocol>http</protocol>
            <username></username>
            <password></password>
            <host>****</host>
            <port>8080</port>
            <nonProxyHosts>***</nonProxyHosts>
        </proxy>
    </proxies>

    
    <servers>
        <server>
            <id>sonatype-nexus-snapshots</id>
            <username>py4fun</username>
            <password>***</password>
        </server>

        <server>
            <id>sonatype-nexus-staging</id>
            <username>py4fun</username>
            <password>***</password>
        </server>
    </servers>


    <profiles>
        <profile>
            <id>gpginfo</id>
            <properties>
                <gpg.keyname>CFCF8216</gpg.keyname>
                <gpg.passphrase>***</gpg.passphrase>
            </properties>
        </profile>
    </profiles>

    <activeProfiles>
        <activeProfile>gpginfo</activeProfile>
    </activeProfiles>

</settings>
```