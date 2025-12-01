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
        Gson gson = new Gson();

        HashMap<String, Class<? extends Transaction>> mapStringToTransactionclass = new HashMap<>();
        mapStringToTransactionclass.put("bank.IncomingTransfer",IncomingTransfer.class);
        mapStringToTransactionclass.put("bank.OutgoingTransfer",OutgoingTransfer.class);
        mapStringToTransactionclass.put("bank.Payment",Payment.class);

        JsonObject jsonTransaction = jsonElement.getAsJsonObject();

        String transactionClass = jsonTransaction.get(("CLASSNAME")).getAsString();
        return gson.fromJson(
                jsonTransaction.get("INSTANCE"),
                mapStringToTransactionclass.get( transactionClass )
        );
    }
}
