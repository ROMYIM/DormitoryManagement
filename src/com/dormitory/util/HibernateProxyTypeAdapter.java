package com.dormitory.util;

import java.io.IOException;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy> {
	
	public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            return (HibernateProxy.class.isAssignableFrom(type.getRawType()) ? (TypeAdapter<T>) new HibernateProxyTypeAdapter(gson) : null);
        }
    };
    private final Gson context;

    private HibernateProxyTypeAdapter(Gson context) {
        this.context = context;
    }

	@Override
	public HibernateProxy read(JsonReader arg0) throws IOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public void write(JsonWriter arg0, HibernateProxy arg1) throws IOException {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			arg0.nullValue();
			return;
		}
		Class<?> baseType = Hibernate.getClass(arg1);
		@SuppressWarnings("rawtypes")
		TypeAdapter delegate = context.getAdapter(TypeToken.get(baseType));
		Object unproxiedValue = ((HibernateProxy) arg1).getHibernateLazyInitializer().getImplementation();
		delegate.write(arg0, unproxiedValue);
	}

}
