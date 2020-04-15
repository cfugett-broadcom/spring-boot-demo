## Spring Boot application with Google Cloud Platform CICD

This application was created as a prototype of a Spring Boot application
being built, statically analyzed, tested, and deployed using Google Cloud
Platform (GCP).

### Getting up and running
In order to get the CICD pipeline up and running there are a few steps that
need to happen first.
1. This project needs to be in a repository that is reachable from GCP. Mine
is in GitHub.
2. You need to log into GCP, and either create or select a project.
3. Under the context of that project, navigate to "Cloud Build > Triggers",
connect your repository, and configure it to trigger based on "push to branch".
The build uses a Cloud Build configuration file, which in turn uses a Dockerfile,
but you will need to set your build configuration to either "Autodetected", or
"Cloud Build configuration file".
4. You need to create an account on sonarcloud.io. This is where the static sonar
scan will publish its results.  Once you've created an account, you will need to
update the build.gradle file in the root of the project with your sonar credentials.
The ability to use the free account dictates that your GitHub project be public.
5. At the moment, I don't know how to capture the url generated during the
deployment phase to pass to the Cypress test execution, so you basically need
to manually trigger the first build, copy the url, and update the baseUrl inside
the "/test/java/com/termalabs/demo/ui/cypress.json" file.

### CICD Pipeline steps
The CICD pipeline attached to the project runs the following steps upon code
commit, which are defined in the couldbuild.yaml file in the root of the project.
1. Clones the repository
2. Runs the './gradlew test' command to run the Unit and Integration tests located
in the "/test/java/com/termalabs/demo/[integration|unit]" directories.
3. Runs a static code analysis with Sonar, and pushes the results to Sonar Cloud.
4. Runs the './gradlew bootJar' command to generate an executable jar file for the
application.
5. Creates a Docker image and copies the executable jar into the root directory of
the image.
6. Pushes the image to Google Cloud Platforms "Container Registry" under the name:
"gcr.io/$PROJECT_ID/demo:$COMMIT_SHA", which is unique for EVERY commit pushed.
7. Deployes the newly created Docker image to Google Cloud Platforms "Cloud Run"
IaaS offering, making it publicly available.
8. Runs the Cypress.io ui tests located in
"/test/java/com/termalabs/demo/ui/cypress/integration" inside a docker container,
which comes pre-packaged with all the dependencies required by Cypress.

### Working on the application
Since the application is a Spring Boot application, there are some neat Spring
Boot Development tools available. One of which is a "Live Reload" server, which
will automatically re-deploy your application and refresh your browser window
when you compile your code.  It used to do it on Save (in Intellij), but it doesn't
now for some reason.  To start, you will need to install the "Live Reload" plugin in
your browser, then you will run your app locally via your IDE or the using the 
"./gradlew bootRun" command. When you make a change, you will kick off a rebuild of
your project in your IDE, or run "./gradlew build" in a separate terminal window. Once
your project rebuilds, the Live Reload service will recognize the change, and restart
the application (quickly), and poke the browser to refresh itself.  This is super
convenient when done in a IDE if you memorize the keyboard shortcut to rebuild, because
you don't have to touch your browser.

### Working on the CICD pipeline
The CICD pipeline is essentially created using the cloudbuild.yaml and Dockerfile files
at the root of the project.  If you make changes to these files and push, the pipeline
will take on the new changes since it's completely based upon that file, and not anything
configured in GCP.  GCP offers a utility that allows you to execute your cloudbuild.yaml
build file locally for development purposes, which is nice, but there are some shortcomings.
To install this tool, you will need to install the Cloud SDK. Here is how to install the
SDK on a mac:
1. Download the SDK from https://cloud.google.com/sdk/docs
2. Unzip into your /Applications directory.
3. Run the "/Applications/google-cloud-sdk/install.sh" install script.
4. Run the "/Applications/google-cloud-sdk/bin/gcloud" init script.
5. Run ". ~/.bashrc" to source your bash rc file.
6. Run "gcloud components install cloud-build-local" to install the local cloud build tool.
7. Run "cloud-build-local --config=cloudbuild.yaml --dryrun=false --push=false --bind-mount-source=false ."
to run your cloudbuild.yaml pipline script locally.

Now for the caveats. Inside the cloudbuild.yaml file, there are some variables that get replaced
by the build server, but don't get replaced when you run locally. For example: $PROJECT_ID, and $COMMIT_SHA.

There is a concept in the cloudbuild.yaml of "Substitutions", which might provide a way to override
those values with defaults if being run locally, I just ran out of time to solve that and the url issue.


