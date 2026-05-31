import { useState } from "react";
import { FormProvider, useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import { AddressForm } from "./AddressForm";
import { ValidationRules } from "./validationRules";
import agent from "../../app/api/agent";
import { useAppDispatch } from "../../app/store/configureStore";
import { setBasket } from "../basket/basketSlice";

const steps = ["Shipping address", "Review your order", "Payment details"];

export function CheckoutPage() {
    const [activeStep, setActiveStep] = useState(0);
    const methods = useForm({ mode: "all", resolver: yupResolver(ValidationRules[activeStep]) });
    const dispatch = useAppDispatch();

    const handleNext = async () => {
        const isValid = await methods.trigger();
        if (isValid) {
            if (activeStep === steps.length - 1) {
                // Lógica de finalizar pedido (mantida similar à sua original)
            } else {
                setActiveStep(prev => prev + 1);
            }
        }
    };

    return (
        <FormProvider {...methods}>
            <div className="max-w-3xl mx-auto my-10 p-6 bg-white dark:bg-neutral-800 rounded-xl shadow-lg">
                <h1 className="text-3xl font-bold text-center mb-8">Checkout</h1>
                
                {/* Stepper customizado com Tailwind */}
                <div className="flex justify-between mb-8">
                    {steps.map((label, idx) => (
                        <div key={label} className={`flex items-center ${idx <= activeStep ? 'text-primary-600' : 'text-neutral-400'}`}>
                            <span className="w-8 h-8 rounded-full border-2 border-current flex items-center justify-center font-bold mr-2">{idx + 1}</span>
                            <span className="font-medium">{label}</span>
                        </div>
                    ))}
                </div>

                <div className="min-h-[300px]">
                    {activeStep === steps.length ? (
                        <div className="text-center space-y-4">
                            <h2 className="text-2xl font-bold">Thank you for your order!</h2>
                            <p>Your order has been placed successfully.</p>
                        </div>
                    ) : (
                        <>
                            {activeStep === 0 && <AddressForm />}
                            {/* Adicione Review e PaymentForm aqui */}
                            <div className="flex justify-end gap-4 mt-8">
                                {activeStep !== 0 && <button onClick={() => setActiveStep(prev => prev - 1)} className="px-6 py-2 rounded-lg border">Back</button>}
                                <button onClick={handleNext} className="px-6 py-2 rounded-lg bg-primary-600 text-white font-bold">
                                    {activeStep === steps.length - 1 ? "Place Order" : "Next"}
                                </button>
                            </div>
                        </>
                    )}
                </div>
            </div>
        </FormProvider>
    );
}