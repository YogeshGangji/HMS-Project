import { Avatar, Button, Divider, Modal, NumberInput, Select, Table, TextInput } from '@mantine/core'
import { IconEdit } from '@tabler/icons-react';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux'
import { DateInput } from "@mantine/dates"
import { useDisclosure } from '@mantine/hooks';
import { doctorDepartment, doctorSpecialization } from '../../../Data/DropDownData';
import { getDoctor, updateDoctor } from '../../../Service/DoctorProfileService';
import { data } from 'react-router-dom';
import { useForm } from '@mantine/form';
import { errorNotification, successNotification } from '../../../Utility/NotificationUtil';
import { formatDate } from '../../../Utility/DateUtility';

const doctor: any = {
    name: "Dr. John Doe",
    email: "john.doe@example.com",
    dob: "1990-05-15",
    phone: "+91 9876543210",
    address: "123, Main Street, Mumbai, India",
    licenseNo: "MD-2024-12345",
    specialization: "Cardiology",
    department: "Cardiology",
    totalExperience: 12,
    profilePicture: "https://randomuser.me/api/portra its/men/75.jpg",
};


const Profile = () => {

    const [editMode, setEdit] = useState(false);
    const [opened, { open, close }] = useDisclosure(false);
    const user = useSelector((state: any) => state.user)
    const [profile, setProfile] = useState<any>({});

    const form = useForm({
        initialValues: {
            dob: '',
            phone: '',
            address: '',
            licenseNo: '',
            specialization: '',
            department: '',
            totalExperience: ''
        },
        validate: {
            dob: (value) => !value ? "Date of Birth is Required " : null,
            phone: (val) => !val ? "phone number is requied" : null,
            address: (val) => !val ? "Address is requied" : null,
            licenseNo: (val) => !val ? "License number is requied" : null,

        }
    });


    useEffect(() => {
        getDoctor(user.profileId).then((data) => {
            setProfile(data);
            form.setValues({
                dob: data.dob ?? '',
                phone: data.phone ?? '',
                address: data.address ?? '',
                licenseNo: data.licenseNo ?? '',
                specialization: data.specialization ?? '',
                department: data.department ?? '',
                totalExperience: data.totalExperience ?? ''

            })
        }).catch((error) => {
            console.log(error);
        })

    }, [user.profileId]);

    const handleSubmit = (values: any) => {

        updateDoctor({ ...profile, ...values }).then((data) => {
            successNotification("Profile Updated Succefully");
            setProfile(data);
            setEdit(false);

        }).catch((error) => {
            errorNotification(error.response.data.errorMessage);
            console.log(error);
        })
    }


    return (


        <form onSubmit={form.onSubmit(handleSubmit, (error) => console.log(error))} className='p-10'>
            <div className=" flex  justify-between  items-center">
                <div className="flex gap-5 items-center">

                    <div className="flex flex-col item-center gap-3 ">

                        <Avatar variant='filled' src="/avtar.png" size={150} alt='its me ' />

                        {editMode && <Button size='sm' onClick={open} variant='filled' >Upload</Button>
                        }
                    </div>

                    <div className="flex flex-col gap-3">
                        <div className="text-3xl font-medium text-neutral-900">{user.name}</div>
                        <div className="text-xl text-neutral-700">{user.email}</div>
                    </div>

                </div>
                {!editMode &&
                    <Button size='md' onClick={() => setEdit(true)} variant='filled' leftSection={<IconEdit />}>Edit</Button>}
                {editMode &&
                    <Button size='md' type='submit' variant='filled' >Save</Button>
                }
            </div>


            <Divider my="xl" />
            <div className="">

                <div className="text-2xl font-medium mb-5 text-neutral-900">Personal Information</div>


                <Table
                    striped
                    stripedColor='primary.1'
                    withRowBorders={false}
                    verticalSpacing="md"
                >
                    <Table.Tbody className="[&>tr]:!mb-3 [&_td]:!w-1/2">

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Date of Birth</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <DateInput {...form.getInputProps("dob")} placeholder="Enter date of birth" />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{formatDate(profile.dob) ?? "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Phone</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <NumberInput {...form.getInputProps("phone")} hideControls maxLength={10} clampBehavior='strict' placeholder="Enter phone number" />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{profile.phone ?? "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Address</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <TextInput  {...form.getInputProps("address")} placeholder="Enter address" />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{profile.address ?? "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">License No</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <TextInput  {...form.getInputProps("licenseNo")} placeholder="Enter license number" />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{profile.licenseNo ?? "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Specialization</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <Select
                                        {...form.getInputProps("specialization")}
                                        placeholder="Select specialization"
                                        data={doctorSpecialization}
                                        searchable
                                        clearable
                                    />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{profile.specialization ?? "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Department</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <Select
                                        placeholder="Select department"
                                        {...form.getInputProps("department")}
                                        data={doctorDepartment}
                                        searchable
                                        clearable
                                    />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{profile.department ?? "-"}</Table.Td>
                            )}
                        </Table.Tr>

                        <Table.Tr>
                            <Table.Td className="font-semibold text-xl">Total Experience</Table.Td>
                            {editMode ? (
                                <Table.Td className="text-xl">
                                    <NumberInput
                                        hideControls
                                        placeholder="Enter total experience"
                                        {...form.getInputProps("totalExperience")}
                                        min={0}
                                        maxLength={2}
                                        max={50}
                                        suffix=" yrs"
                                    />
                                </Table.Td>
                            ) : (
                                <Table.Td className="text-xl">{profile.totalExperience? profile.totalExperience + "yrs" : "-"} yrs</Table.Td>
                            )}
                        </Table.Tr>

                    </Table.Tbody>
                </Table>
            </div>
            <Modal centered opened={opened} onClose={close} title={<span className='text-xl font-medium'>Upload Profile Picture</span>}>

            </Modal>

        </form >
    )
}

export default Profile