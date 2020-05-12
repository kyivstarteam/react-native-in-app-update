require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-in-app-update"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-in-app-update
                   DESC
  s.homepage     = "https://github.com/kyivstarteam/react-native-in-app-update"
  s.license      = "MIT"
  s.authors      = { "Kyivstar Digital" => "" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/kyivstarteam/react-native-in-app-update.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,m,swift}"
  s.requires_arc = true

  s.dependency "React"
end

