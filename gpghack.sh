# this is a dirty hack, but without it signing artifcats fails using Github Actions.
# After a whole day of research I have not found another way to set this, when not using a pom.xml
# Probably related to https://github.com/actions/runner/issues/241

gpg --pinentry-mode loopback "$@"