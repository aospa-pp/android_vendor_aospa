#
# SPDX-FileCopyrightText: Paranoid Android
# SPDX-License-Identifier: Apache-2.0
#

# Overlays
PRODUCT_PACKAGES += \
    AOSPABlackThemeOverlay \
    AOSPAFrameworksOverlay \
    AOSPAImmersiveNavigationOverlay \
    AOSPANetworkStackOverlay \
    AOSPANetworkStackMainlineOverlay \
    AOSPASettingsOverlay \
    AOSPASettingsProviderOverlay \
    AOSPASystemUIOverlay \
    FontComfortaaOverlay \
    FontExotwoOverlay \
    FontFiraSansOverlay \
    FontGoogleSansOverlay \
    FontHarmonySansOverlay \
    FontHKGroteskOverlay \
    FontInterOverlay \
    FontLinotteSourceOverlay \
    FontManropeOverlay \
    FontMiSansOverlay \
    FontNotoSansOverlay \
    FontOnePlusSansOverlay  \
    FontOppoSansOverlay \
    FontRecursiveCasualOverlay \
    FontRecursiveLinearOverlay \
    FontSourceSansOverlay \
    FontSourceSerifOverlay \
    FontSpaceGroteskOverlay \
    FontStoropiaOverlay \
    FontUrbanistOverlay \
    FontVolteOverlay \
    ParanoidLauncherOverlay \
    PixelThemesStub

PRODUCT_ENFORCE_RRO_EXCLUDED_OVERLAYS += vendor/aospa/overlay/static
PRODUCT_PACKAGE_OVERLAYS += vendor/aospa/overlay/static
