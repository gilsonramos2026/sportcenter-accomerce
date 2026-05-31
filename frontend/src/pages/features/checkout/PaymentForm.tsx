import { useFormContext } from "react-hook-form";

export function PaymentForm() {
    const { register, formState: { errors } } = useFormContext();
    const inputClasses = "w-full p-2 border rounded-lg dark:bg-neutral-800";
    const errorClasses = "border-red-500";

    return (
        <div className="space-y-4">
            <h2 className="text-xl font-bold">Payment Details</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <input {...register("cardName")} placeholder="Name on card" className={`${inputClasses} ${errors.cardName ? errorClasses : ""}`} />
                <input {...register("cardNumber")} placeholder="Card number" className={`${inputClasses} ${errors.cardNumber ? errorClasses : ""}`} />
                <input {...register("expDate")} placeholder="Expiry date (MM/YY)" className={`${inputClasses} ${errors.expDate ? errorClasses : ""}`} />
                <input {...register("cvv")} placeholder="CVV" className={`${inputClasses} ${errors.cvv ? errorClasses : ""}`} />
                <label className="flex items-center gap-2 cursor-pointer">
                    <input type="checkbox" {...register("saveCard")} className="rounded border-neutral-300" />
                    <span>Remember card for next time</span>
                </label>
            </div>
        </div>
    );
}