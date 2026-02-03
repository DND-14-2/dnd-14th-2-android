pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // https://docs.gradle.org/current/userguide/filtering_repository_content.html
        // forRepository에 선언된 kakao 저장소는 filter에 선언된 라이브러리 그룹만 조회
        exclusiveContent {
            forRepository {
                maven {
                    url = uri("https://devrepo.kakao.com/nexus/content/groups/public/")
                }
            }
            filter {
                includeGroup("com.kakao.sdk")
            }
        }
    }
}

rootProject.name = "Pickle"
include(":app")
include(":domain")
include(":presentation")
include(":data")
