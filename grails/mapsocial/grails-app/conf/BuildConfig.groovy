/*grails.war.dependencies = {
	fileset(dir: "grails-app") {
		include(name: "migrations/**")
	}
}*/

grails.war.resources = { stagingDir, args ->
	copy(file: "grails-app/migrations/changelog.xml", tofile: "${stagingDir}/WEB-INF/changelog.xml")
}
