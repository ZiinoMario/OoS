package bank;

import com.google.gson.*;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;

public class TransactionSerializable implements Serializable, JsonSerializer<Transaction>, JsonDeserializer<Transaction> {
    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonTransaction = new JsonObject();
        jsonTransaction.addProperty("CLASSNAME",transaction.getClass().getName());
        jsonTransaction.add("INSTANCE",gson.toJsonTree(transaction));
        return jsonTransaction;
    }

    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashMap<String, Class<? extends Transaction>> getTransactionClassFromStringMap = new HashMap<>();
        getTransactionClassFromStringMap.put("bank.IncomingTransfer",IncomingTransfer.class);
        getTransactionClassFromStringMap.put("bank.OutgoingTransfer",OutgoingTransfer.class);
        getTransactionClassFromStringMap.put("bank.Payment",Payment.class);

        JsonObject jsonObjTransaction = jsonElement.getAsJsonObject();
        getTransactionClassFromStringMap.get(jsonObjTransaction.get("CLASSNAME"));


        return null;
    }
}
