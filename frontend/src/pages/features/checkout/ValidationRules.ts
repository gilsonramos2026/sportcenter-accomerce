import * as yup from 'yup';

export const ValidationRules = [
    // Step 0: Shipping Address
    yup.object({
        firstName: yup.string().required('First name is required'),
        lastName: yup.string().required('Last name is required'),
        address1: yup.string().required('Address line 1 is required'),
        address2: yup.string().nullable(),
        city: yup.string().required('City is required'),
        state: yup.string().required('State is required'),
        zip: yup.string().required('Zip code is required').matches(/^\d+$/, 'Zip code must be numbers'),
        country: yup.string().required('Country is required'),
    }),
    
    // Step 1: Review (geralmente não precisa de validação de schema)
    yup.object(),

    // Step 2: Payment Details
    yup.object({
        cardName: yup.string().required('Name on card is required'),
        cardNumber: yup.string()
            .required('Card number is required')
            .matches(/^[0-9]{16}$/, 'Must be exactly 16 digits'),
        expDate: yup.string()
            .required('Expiry date is required')
            .matches(/^(0[1-9]|1[0-2])\/?([0-9]{2})$/, 'Must be in MM/YY format'),
        cvv: yup.string()
            .required('CVV is required')
            .matches(/^[0-9]{3,4}$/, 'CVV must be 3 or 4 digits')
    })
];