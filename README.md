The Vietmaps Navigation SDK for Android is built on top of [the Vietmaps Directions API] and contains logic needed to get timed navigation instructions.

## Getting Started

Add this snippet to your `build.gradle` file to use this SDK (`libandroid-navigation`):

```
implementation 'com.vietmaps.vietmapssdk:vietmaps-android-navigation:1.0.0'
```

And for `libandroid-navigation-ui`:

```
implementation 'com.vietmaps.vietmapssdk:vietmaps-android-navigation-ui:1.0.0'
```
**Note**:  When using the UI library, you _do not_ need to add both dependencies.  The UI library will automatically pull in `libandroid-navigation`.

**Important Note**: You _must_ include the following snippet in your top project-level `build.gradle` file:
```
repositories {
    maven { url 'https://dl.bintray.com/rosaliza/vietmaps' }
}
```

This will ensure the `Vietmaps` dependency is properly downloaded.

To run the sample code on a device or emulator, include your developer access token in `developer-config.xml` found in the project.
## Documentation

You'll find all of the documentation for this SDK on our Vietmaps Navigation page. This includes information on installation, using the API, and links to the API reference.