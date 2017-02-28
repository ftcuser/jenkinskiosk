require 'serverspec'

DOCKER_REPO = ENV.fetch('REPO_NAME', 'jenkinsmanager')
DOCKER_IMAGE = ENV.fetch('IMAGE_TAG', 'latest')

set :backend, :docker

RSpec.configure do |c|
  c.docker_image = "#{DOCKER_REPO}:#{DOCKER_IMAGE}"

  if ENV['ASK_SUDO_PASSWORD']
    require 'highline/import'
    c.sudo_password = ask("Enter sudo password: ") { |q| q.echo = false }
  else
    c.sudo_password = ENV['SUDO_PASSWORD']
  end
end