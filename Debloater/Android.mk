LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := Debloater
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_TAGS := optional
LOCAL_OVERRIDES_PACKAGES += \
    Browser \
    Browser2 \
    Calendar \
    Chrome64 \
    Contacts \
    DeskClock \
    Dialer \
    Drive \
    Email \
    Gallery \
    Gallery2 \
    Gmail2 \
    Maps \
    MusicFX \
    messaging \
    PersonalSafety \
    YouTube \
    GoogleFeedback \
	FamilyLinkParentalControls
LOCAL_UNINSTALLABLE_MODULE := true
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_SRC_FILES := /dev/null
include $(BUILD_PREBUILT)
