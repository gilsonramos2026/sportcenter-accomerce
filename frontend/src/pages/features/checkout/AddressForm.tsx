import { useFormContext } from "react-hook-form";

export function AddressForm() {
    const { register, formState: { errors } } = useFormContext();

    const inputClasses = "w-full p-2 border rounded-lg dark:bg-neutral-800";
    const errorClasses = "border-red-500 focus:ring-red-500";

    return (
        <div className="space-y-4">
            <h2 className="text-xl font-bold">Shipping Address</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <input {...register("firstName")} placeholder="First Name" className={`${inputClasses} ${errors.firstName ? errorClasses : ""}`} />
                <input {...register("lastName")} placeholder="Last Name" className={`${inputClasses} ${errors.lastName ? errorClasses : ""}`} />
                <input {...register("address1")} placeholder="Address Line 1" className={`md:col-span-2 ${inputClasses} ${errors.address1 ? errorClasses : ""}`} />
                <input {...register("address2")} placeholder="Address Line 2" className={`md:col-span-2 ${inputClasses}`} />
                <input {...register("city")} placeholder="City" className={`${inputClasses} ${errors.city ? errorClasses : ""}`} />
                <input {...register("state")} placeholder="State" className={`${inputClasses} ${errors.state ? errorClasses : ""}`} />
                <input {...register("zip")} placeholder="Zip Code" className={`${inputClasses} ${errors.zip ? errorClasses : ""}`} />
                <input {...register("country")} placeholder="Country" className={`${inputClasses} ${errors.country ? errorClasses : ""}`} />
            </div>
        </div>
    );
}