package click.dobel.alexasonic.speechlet;

import static com.greghaskins.spectrum.Spectrum.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Locale;
import java.util.Optional;

import org.junit.runner.RunWith;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.Context;
import com.amazon.speech.speechlet.Device;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletRequest;
import com.amazon.speech.speechlet.User;
import com.amazon.speech.speechlet.interfaces.audioplayer.AudioPlayerState;
import com.amazon.speech.speechlet.interfaces.audioplayer.PlayerActivity;
import com.amazon.speech.speechlet.interfaces.system.SystemState;
import com.greghaskins.spectrum.Spectrum;

import click.dobel.alexasonic.exception.AlexaSonicException;
import click.dobel.alexasonic.speechlet.requestcontext.RequestContext;

@RunWith(Spectrum.class)
public class SpeechletRequestUtilsSpecTest {

    private static final String TEST_REQUEST_ID = "SomeRequestId";
    private static final String TEST_DEVICE_ID = "DeviceTestId-9876543210";
    private static final String TEST_USER_ID = "UserTestId-9876543210";
    private static final String SHORT_ID = "76543210";

    {

        describe(SpeechletRequestUtils.class.getSimpleName(), () -> {
            describe("#getDeviceId", () -> {
                it("returns the device Id when it is contained in the request", () -> {
                    final Device device = Device.builder().withDeviceId(TEST_DEVICE_ID).build();
                    final SystemState state = SystemState.builder().withDevice(device).build();
                    final Context context = Context.builder().addState(state).build();
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().withContext(context)
                            .build();
                    assertThat(SpeechletRequestUtils.getDeviceId(requestEnvelope)).isEqualTo(TEST_DEVICE_ID);
                });
                it("throws an exeption when the request contains no device id", () -> {
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().build();
                    assertThatExceptionOfType(AlexaSonicException.class)
                            .isThrownBy(() -> SpeechletRequestUtils.getDeviceId(requestEnvelope))
                            .withMessage(RequestContext.MESSAGE_NODEVICEID);
                });
            });

            describe("#getShortDeviceId", () -> {
                it("returns the 8 last characters of the device id", () -> {
                    final Device device = Device.builder().withDeviceId(TEST_DEVICE_ID).build();
                    final SystemState state = SystemState.builder().withDevice(device).build();
                    final Context context = Context.builder().addState(state).build();
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().withContext(context)
                            .build();
                    assertThat(SpeechletRequestUtils.getShortDeviceId(requestEnvelope)).isEqualTo(SHORT_ID);
                });
            });

            describe("#getUserId", () -> {
                it("returns the user Id when it is contained in the request", () -> {
                    final User user = User.builder().withUserId(TEST_USER_ID).build();
                    final SystemState state = SystemState.builder().withUser(user).build();
                    final Context context = Context.builder().addState(state).build();
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().withContext(context)
                            .build();
                    assertThat(SpeechletRequestUtils.getUserId(requestEnvelope)).isEqualTo(TEST_USER_ID);
                });
                it("throws an exeption when the request contains no device id", () -> {
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().build();
                    assertThatExceptionOfType(AlexaSonicException.class)
                            .isThrownBy(() -> SpeechletRequestUtils.getUserId(requestEnvelope))
                            .withMessage(RequestContext.MESSAGE_NOUSERID);
                });
            });

            describe("#getShortUserId", () -> {
                it("returns the 8 last characters of the user id", () -> {
                    final User user = User.builder().withUserId(TEST_USER_ID).build();
                    final SystemState state = SystemState.builder().withUser(user).build();
                    final Context context = Context.builder().addState(state).build();
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().withContext(context)
                            .build();
                    assertThat(SpeechletRequestUtils.getShortUserId(requestEnvelope)).isEqualTo(SHORT_ID);
                });
            });

            describe("#getLocale", () -> {
                it("returns english if the the request contains no locale", () -> {
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().build();
                    assertThat(SpeechletRequestUtils.getLocale(requestEnvelope)).isEqualTo(Locale.ENGLISH);
                });

                it("returns the locale of the request if it is contained", () -> {
                    final SpeechletRequest request = IntentRequest.builder().withRequestId(TEST_REQUEST_ID)
                            .withLocale(Locale.CANADA_FRENCH).build();
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().withRequest(request)
                            .build();
                    assertThat(SpeechletRequestUtils.getLocale(requestEnvelope)).isEqualTo(Locale.CANADA_FRENCH);
                });
            });

            describe("#getAudioPlayerState", () -> {
                it("returns the audioplayer state of the request", () -> {
                    final AudioPlayerState state = createAudioPlayerState();
                    final Context context = Context.builder().addState(state).build();
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope //
                            .builder() //
                            .withContext(context) //
                            .build();

                    assertThat(SpeechletRequestUtils.getAudioPlayerState(requestEnvelope)).isEqualTo(Optional.of(state));
                });
                it("returns an empty audioplayer state when the request has none", () -> {
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().build();
                    assertThat(SpeechletRequestUtils.getAudioPlayerState(requestEnvelope)).isEqualTo(Optional.empty());
                });
            });

            describe("#getAudioPlayerToken", () -> {
                it("returns the audioplayer token of the request", () -> {
                    final AudioPlayerState state = createAudioPlayerState();
                    final Context context = Context.builder().addState(state).build();
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope //
                            .builder() //
                            .withContext(context) //
                            .build();

                    assertThat(SpeechletRequestUtils.getAudioPlayerToken(requestEnvelope)).isEqualTo(state.getToken());
                });
                it("throws an exception when the request has no token (not playing)", () -> {
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().build();
                    assertThatExceptionOfType(AlexaSonicException.class) //
                            .isThrownBy(() -> SpeechletRequestUtils.getAudioPlayerToken(requestEnvelope))//
                            .withMessage(SpeechletRequestUtils.MESSAGEKEY_NOT_PLAYING);
                });
            });

            describe("#getAudioPlayerOffsetInMilliseconds", () -> {
                it("returns the audioplayer offset of the request", () -> {
                    final AudioPlayerState state = createAudioPlayerState();
                    final Context context = Context.builder().addState(state).build();
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope //
                            .builder() //
                            .withContext(context) //
                            .build();

                    assertThat(SpeechletRequestUtils.getAudioPlayerOffsetInMilliseconds(requestEnvelope))
                            .isEqualTo(state.getOffsetInMilliseconds());
                });
                it("throws an exception when the request has no offset (not playing)", () -> {
                    final SpeechletRequestEnvelope<?> requestEnvelope = SpeechletRequestEnvelope.builder().build();
                    assertThatExceptionOfType(AlexaSonicException.class) //
                            .isThrownBy(() -> SpeechletRequestUtils.getAudioPlayerOffsetInMilliseconds(requestEnvelope))//
                            .withMessage(SpeechletRequestUtils.MESSAGEKEY_NOT_PLAYING);
                });
            });

        });

    }

    private AudioPlayerState createAudioPlayerState() {
        return AudioPlayerState.builder() //
                .withOffsetInMilliseconds(25) //
                .withPlayerActivity(PlayerActivity.BUFFER_UNDERRUN) //
                .withToken("TOK") //
                .build();
    }
}
