# oVirt Engine API Metamodel

[![Copr build status](https://copr.fedorainfracloud.org/coprs/ovirt/ovirt-master-snapshot/package/ovirt-engine-api-metamodel/status_image/last_build.png)](https://copr.fedorainfracloud.org/coprs/ovirt/ovirt-master-snapshot/package/ovirt-engine-api-metamodel/)

Welcome to the oVirt Engine API Metamodel source repository. This repository is hosted on [GitHub:ovirt-engine-api-metamodel](https://github.com/oVirt/ovirt-engine-api-metamodel).

This project contains the oVirt Engine API Metamodel. It is a set of tools that read, analyze and generate code from the API model.

## Building

To build this project use the usual Maven command line:

  ```bash
  $ mvn clean install
  ```

## Releasing

The project is released to Maven Central via Sonatype Central.

To perform a release you will need to do the following actions, most of
them automated by the Maven release plugin:

### Prepare the release

Create a settings.xml file with the following content:

```xml
<settings>
    <servers>
        <server>
            <id>central</id>
            <username>${generated-mvn-central-username}</username>
            <password>${generated-mvn-central-pwd}</password>
        </server>
    </servers>
    <profiles>
        <profile>
            <id>central</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <gpg.executable>gpg2</gpg.executable>
                <gpg.passphrase>${personal-gpg-passphrase}</gpg.passphrase>
            </properties>
        </profile>
    </profiles>
</settings>
```

The rest is automated using the Maven release plugin:

  $ mvn release:prepare

This will ask you the version numbers to use for the released artifacts
and the version numbers to use after the release. The release version
numbers will be something like 1.0.4, and the version numbers after the
release will be something like 1.0.5-SNAPSHOT. You should use the
defaults unless there is a very good reason to change them.

The result will be two new patches, and a tag added to the local
repository. These patches and tag will *not* be pushed automatically to
the remote repository, so you need to do it manually, first the patches:

  $ git push origin HEAD:refs/for/master

This will send the patches for review to https://gerrit.ovirt.org[gerrit].
Go there, review and merge them. Once the patches are merged the tag can
be pushed:

  $ git push origin 1.0.4

### Perform the release

To deploy a new release to maven central bump the version tag or add `-SNAPSHOT` and run:

  ```bash
  $ mvn deploy -Psign
  ```

NOTE: before the artifacts needed to be signed manually but now this is all contained in the deploy command.

NOTE: The artifacts will be signed using your default GPG key, so make
sure you have a valid GPG key available.

This will use the tag to checkout the code from the remote repository,
it will build it, run the tests and, finally, if everything succeeds, it
will upload the signed artifacts.

The rest of the process is manual, using the Maven central web interface available [here](https://central.sonatype.com/publishing). Log in with your username and password. If you have the right permissions you should see a deployment action running. After validating that everything is signed and the javadoc is present in the release the option to publish should become available.

NOTE: If releasing under the `-SNAPSHOT` suffix, please mind that currently these releases get cleaned up after a period of 90 days. see [the documentation](https://central.sonatype.org/publish/publish-portal-snapshots/) for more info.

## How to contribute

All contributions are welcome - patches, bug reports, and documentation issues.

### Submitting patches

Please submit patches to [GitHub:ovirt-engine-api-metamodel](https://github.com/oVirt/ovirt-engine-api-metamodel). If you are not familiar with the process, you can read about [collaborating with pull requests](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests) on the GitHub website.

### Found a bug or documentation issue?

To submit a bug or suggest an enhancement for oVirt Engine API Metamodel please use [oVirt Bugzilla for ovirt-engine product](https://bugzilla.redhat.com/enter_bug.cgi?product=ovirt-engine).

If you don't have a Bugzilla account, you can still report [issues](https://github.com/oVirt/ovirt-engine-api-metamodel/issues). If you find a documentation issue on the oVirt website, please navigate to the page footer and click "Report an issue on GitHub".

## Still need help?

If you have any other questions or suggestions, you can join and contact us on the [oVirt Users forum / mailing list](https://lists.ovirt.org/admin/lists/users.ovirt.org/) or join the [Matrix channel](https://matrix.to/#/#support:ovirt.tech).
