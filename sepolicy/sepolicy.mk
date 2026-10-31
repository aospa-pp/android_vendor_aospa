#
# SPDX-FileCopyrightText: Paranoid Android
# SPDX-License-Identifier: Apache-2.0
#

SYSTEM_EXT_PRIVATE_SEPOLICY_DIRS += \
    vendor/aospa/sepolicy/private

SYSTEM_EXT_PUBLIC_SEPOLICY_DIRS += \
    vendor/aospa/sepolicy/public

BOARD_VENDOR_SEPOLICY_DIRS += \
    vendor/aospa/sepolicy/vendor

# Lineage health (charging control) sepolicy.  Pulled in only when the device
# actually builds the vendor.lineage.health HAL, so that targets without the
# feature don't ship its policy.
ifneq ($(filter vendor.lineage.health-service.default,$(PRODUCT_PACKAGES)),)
SYSTEM_EXT_PUBLIC_SEPOLICY_DIRS += \
    vendor/aospa/sepolicy/lineage_health/public

SYSTEM_EXT_PRIVATE_SEPOLICY_DIRS += \
    vendor/aospa/sepolicy/lineage_health/private

BOARD_VENDOR_SEPOLICY_DIRS += \
    vendor/aospa/sepolicy/lineage_health/vendor
endif
