load("@batfish//skylark:junit.bzl", "junit_tests")

package(
    default_testonly = True,
    default_visibility = ["//visibility:public"],
)

junit_tests(
    name = "tests",
    srcs = glob([
        "**/*Test.java",
    ]),
    resources = [
        "//projects/batfish/src/test/resources:log4j_config",
        "//projects/batfish/src/test/resources/org/batfish/vendor/check_point_gateway/grammar/testconfigs",
        "//projects/batfish/src/test/resources/org/batfish/vendor/check_point_gateway/grammar/snapshots",
    ],
    tags = ["cpu:4"],
    runtime_deps = [
        "@maven//:org_apache_logging_log4j_log4j_core",
        "@maven//:org_apache_logging_log4j_log4j_slf4j_impl",
    ],
    deps = [
        "//projects/batfish",
        "//projects/batfish:batfish_testlib",
        "//projects/batfish-common-protocol:common",
        "//projects/batfish-common-protocol/src/test/java/org/batfish/common/matchers",
        "//projects/batfish-common-protocol/src/test/java/org/batfish/datamodel/matchers",
        "//projects/batfish/src/main/java/org/batfish/vendor/check_point_gateway/grammar",
        "//projects/batfish/src/main/java/org/batfish/vendor/check_point_gateway/representation",
        "//projects/batfish/src/main/java/org/batfish/vendor/check_point_management",
        "//projects/batfish/src/test/java/org/batfish/vendor/check_point_management:testlib",
        "@maven//:com_google_code_findbugs_jsr305",
        "@maven//:com_google_guava_guava",
        "@maven//:junit_junit",
        "@maven//:org_antlr_antlr4_runtime",
        "@maven//:org_apache_commons_commons_lang3",
        "@maven//:org_hamcrest_hamcrest",
    ],
)
