package crypto

import javax.crypto.spec.SecretKeySpec
import java.security.Key


class DefaultKeyDeserializer {
    fun deserialize(serialized: ByteArray): Key {
        return SecretKeySpec(serialized, KeySettings.algotithmSpec)
    }
}