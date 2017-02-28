require 'spec_helper'

JAVA_VERSION = '1.7.0_'

describe file('/usr/bin/java') do
  it { should be_symlink }
  it { should be_linked_to('/etc/alternatives/java') }
end

describe command('java -version') do
  its(:stderr) { should match(/"#{JAVA_VERSION}/) }
end

#describe file('/usr/lib/jvm/java/jre/lib/security/java.security') do
#  its(:content) { should match(/networkaddress\.cache\.ttl=60/) }
#end